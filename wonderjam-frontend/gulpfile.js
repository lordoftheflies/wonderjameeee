/**
 * @license
 * Copyright (c) 2016 The Polymer Project Authors. All rights reserved.
 * This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
 * The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
 * The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
 * Code distributed by Google as part of the polymer project is also
 * subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
 */

'use strict';

const path = require('path');
const gulp = require('gulp');
const gulpif = require('gulp-if');

const gutil = require('gulp-util');
const debug = require('gulp-debug');
const gulpignore = require('gulp-ignore');
const gulpmatch = require('gulp-match');
const sort = require('gulp-sort');
const grepContents = require('gulp-grep-contents');
const size = require('gulp-size');
const merge = require('gulp-merge');
const through = require('through2');
const stripBom = require('strip-bom');
const JSONstringify = require('json-stringify-safe');

const i18nPreprocess = require('gulp-i18n-preprocess');
const i18nLeverage = require('gulp-i18n-leverage');
const XliffConv = require('xliff-conv');
const i18nAddLocales = require('gulp-i18n-add-locales');

// Got problems? Try logging 'em
// const logging = require('plylog');
// logging.setVerbose();

// Global object to store localizable attributes repository
let attributesRepository = {};

// Bundles object
let prevBundles = {};
let bundles = {};

let title = 'I18N transform';
let tmpDir = '.tmp';

let xliffOptions = {};

// Scan HTMLs and construct localizable attributes repository
let scan = gulpif('*.html', i18nPreprocess({
  constructAttributesRepository: true, // construct attributes repository
  attributesRepository: attributesRepository, // output object
  srcPath: '.', // path to source root
  attributesRepositoryPath: 
    'bower_components/i18n-behavior/i18n-attr-repo.html', // path to i18n-attr-repo.html
  dropHtml: false // do not drop HTMLs
}));

let basenameSort = sort({
  comparator: function(file1, file2) {
    var base1 = path.basename(file1.path).replace(/^bundle[.]/, ' bundle.');
    var base2 = path.basename(file2.path).replace(/^bundle[.]/, ' bundle.');
    return base1.localeCompare(base2);
  }
});

let dropDefaultJSON = gulpignore([ 'src/**/*.json', '!**/locales/*.json' ]);

let preprocess = gulpif('*.html', i18nPreprocess({
  replacingText: true, // replace UI texts with {{annotations}}
  jsonSpace: 2, // JSON format with 2 spaces
  srcPath: '.', // path to source root
  attributesRepository: attributesRepository // input attributes repository
}));

let tmpJSON = gulpif([ 'src/**/*.json', '!src/**/locales/*' ], gulp.dest(tmpDir));

let unbundleFiles = [];
let importXliff = through.obj(function (file, enc, callback) {
  // bundle files must come earlier
  unbundleFiles.push(file);
  callback();
}, function (callback) {
  let match;
  let file;
  let bundleFileMap = {};
  let xliffConv = new XliffConv(xliffOptions);
  while (unbundleFiles.length > 0) {
    file = unbundleFiles.shift();
    if (path.basename(file.path).match(/^bundle[.]json$/)) {
      prevBundles[''] = JSON.parse(stripBom(String(file.contents)));
      bundleFileMap[''] = file;
    }
    else if (match = path.basename(file.path).match(/^bundle[.]([^.\/]*)[.]json$/)) {
      prevBundles[match[1]] = JSON.parse(stripBom(String(file.contents)));
      bundleFileMap[match[1]] = file;
    }
    else if (match = path.basename(file.path).match(/^bundle[.]([^.\/]*)[.]xlf$/)) {
      xliffConv.parseXliff(String(file.contents), { bundle: prevBundles[match[1]] }, function (output) {
        if (bundleFileMap[match[1]]) {
          bundleFileMap[match[1]].contents = new Buffer(JSONstringify(output, null, 2));
        }
      });
    }
    else if (gulpmatch(file, '**/locales/*.json') &&
             (match = path.basename(file.path, '.json').match(/^([^.]*)[.]([^.]*)/))) {
      if (prevBundles[match[2]] && prevBundles[match[2]][match[1]]) {
        file.contents = new Buffer(JSONstringify(prevBundles[match[2]][match[1]], null, 2));
      }
    }
    this.push(file);
  }
  callback();
});

let leverage = gulpif([ 'src/**/locales/*.json', '!**/locales/bundle.*.json' ], i18nLeverage({
  jsonSpace: 2, // JSON format with 2 spaces
  srcPath: '', // path to source root
  distPath: '/' + tmpDir, // path to dist root to fetch next default JSON files
  bundles: bundles // output bundles object
}));

let bundleFiles = [];
let exportXliff = through.obj(function (file, enc, callback) {
  bundleFiles.push(file);
  callback();
}, function (callback) {
  let file;
  let cwd = bundleFiles[0].cwd;
  let base = bundleFiles[0].base;
  let xliffConv = new XliffConv(xliffOptions);
  let srcLanguage = 'en';
  let promises = [];
  let self = this;
  let lang;
  while (bundleFiles.length > 0) {
    file = bundleFiles.shift();
    if (!gulpmatch(file, [ '**/bundle.json', '**/locales/bundle.*.json', '**/xliff/bundle.*.xlf' ])) {
      this.push(file);
    }
  }
  for (lang in bundles) {
    bundles[lang].bundle = true;
    this.push(new gutil.File({
      cwd: cwd,
      base: base,
      path: lang ? path.join(cwd, 'locales', 'bundle.' + lang + '.json')
                 : path.join(cwd, 'bundle.json'),
      contents: new Buffer(JSONstringify(bundles[lang], null, 2))
    }));
  }
  for (lang in bundles) {
    if (lang) {
      (function (destLanguage) {
        promises.push(new Promise(function (resolve, reject) {
          xliffConv.parseJSON(bundles, {
            srcLanguage: srcLanguage,
            destLanguage: destLanguage
          }, function (output) {
            self.push(new gutil.File({
              cwd: cwd,
              base: base,
              path: path.join(cwd, 'xliff', 'bundle.' + destLanguage + '.xlf'),
              contents: new Buffer(output)
            }));
            resolve();
          });
        }));
      })(lang);
    }
  }
  Promise.all(promises).then(function (outputs) {
    callback();
  });
});

let feedback = gulpif([ '**/bundle.json', '**/locales/*.json', '**/src/**/*.json', '**/xliff/bundle.*.xlf' ], gulp.dest('.'));

// !!! IMPORTANT !!! //
// Keep the global.config above any of the gulp-tasks that depend on it
global.config = {
  polymerJsonPath: path.join(process.cwd(), 'polymer.json'),
  build: {
    rootDirectory: 'build',
    bundledDirectory: 'bundled',
    unbundledDirectory: 'unbundled',
    // Accepts either 'bundled', 'unbundled', or 'both'
    // A bundled version will be vulcanized and sharded. An unbundled version
    // will not have its files combined (this is for projects using HTTP/2
    // server push). Using the 'both' option will create two output projects,
    // one for bundled and one for unbundled
    bundleType: 'both'
  },
  // Path to your service worker, relative to the build root directory
  serviceWorkerPath: 'service-worker.js',
  // Service Worker precache options based on
  // https://github.com/GoogleChrome/sw-precache#options-parameter
  swPrecacheConfig: {
    navigateFallback: '/index.html'
  },
  // list of target locales to add
  locales: gutil.env.targets ? gutil.env.targets.split(/ /) : []
};

// Add your own custom gulp tasks to the gulp-tasks directory
// A few sample tasks are provided for you
// A task should return either a WriteableStream or a Promise
const clean = require('./gulp-tasks/clean.js');
const images = require('./gulp-tasks/images.js');
const project = require('./gulp-tasks/project.js');

// The source task will split all of your source files into one
// big ReadableStream. Source files are those in src/** as well as anything
// added to the sourceGlobs property of polymer.json.
// Because most HTML Imports contain inline CSS and JS, those inline resources
// will be split out into temporary files. You can use gulpif to filter files
// out of the stream and run them through specific tasks. An example is provided
// which filters all images and runs them through imagemin
function source() {
  return project.splitSource()
    // Add your own build tasks here!
    // I18N processes
    .pipe(scan)
    .pipe(basenameSort)
    .pipe(dropDefaultJSON)
    .pipe(preprocess)
    .pipe(tmpJSON)
    .pipe(importXliff)
    .pipe(leverage)
    .pipe(exportXliff)
    .pipe(feedback)
    .pipe(debug({ title: title }))
    .pipe(size({ title: title }))
    .pipe(gulpif('**/*.{png,gif,jpg,svg}', images.minify()))
    .pipe(project.rejoin()); // Call rejoin when you're finished
}

// The dependencies task will split all of your bower_components files into one
// big ReadableStream
// You probably don't need to do anything to your dependencies but it's here in
// case you need it :)
function dependencies() {
  return project.splitDependencies()
    .pipe(project.rejoin());
}

// Clean the build directory, split all source and dependency files into streams
// and process them, and output bundled and unbundled versions of the project
// with their own service workers
gulp.task('default', gulp.series([
  clean.build,
  project.merge(source, dependencies),
  project.serviceWorker
]));

// Gulp task to add locales to I18N-ready elements and pages
// Usage: gulp locales --targets="{space separated list of target locales}"
gulp.task('locales', function() {
  let elements = gulp.src([ 'src/**/*.html' ], { base: '.' })
    .pipe(grepContents(/i18n-behavior.html/))
    .pipe(grepContents(/<dom-module /));

  let pages = gulp.src([ 'index.html' ], { base: '.' })
    .pipe(grepContents(/is=['"]i18n-dom-bind['"]/));

  return merge(elements, pages)
    .pipe(i18nAddLocales(global.config.locales))
    .pipe(gulp.dest('.'))
    .pipe(debug({ title: 'Add locales:'}))
});
