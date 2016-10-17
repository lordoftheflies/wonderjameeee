/**
 * @license
 * Copyright (c) 2016 The Polymer Project Authors. All rights reserved.
 * This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
 * The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
 * The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
 * Code distributed by Google as part of the polymer project is also
 * subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
 */
console.info('Service worker disabled for development, will be generated at build time.');

var backendUrl = '/backend';
var baseUrl = '/';

console.debug('Service-worker started', self);

self.addEventListener('push', function (event) {
    console.debug('Service-worker received a push message.', event);
    self.registration.pushManager.getSubscription()
            .then(function (sub) {
                var endpointUrl = sub.endpoint.split('/');
                var subscriptionId = endpointUrl[endpointUrl.length - 1];
                var notificationBackendUrl = backendUrl + '/mailbox/notifications?subscriptionId=' + encodeURIComponent(subscriptionId);
                console.log('Fetch notifications from ' + notificationBackendUrl);
                fetch(notificationBackendUrl)
                        .then(function (response) {
                            return response.json();
                        })
                        .then(function (payload) {
                            payload.forEach(function (currentValue, index, arr) {
                                console.log('Notify user', payload);
                                self.registration.showNotification(currentValue.fromName + ": " + currentValue.subject, {
                                    body: currentValue.message,
                                    icon: baseUrl + '/images/favicon-64x64.png',
                                    tag: 'topflavon-notification-' + currentValue.id
                                });
                            }, self);
                        })
                        .catch(function (e) {
                            console.log("Error fetching notifications", e.stack);
                        });
            })
            .catch(function (e) {
                console.log("Error subscription id", e.stack);
            });

});

self.addEventListener('notificationclick', function (event) {
    console.debug('Service worker: click on notification', event.notification, event.data);
    event.notification.close();
    
    var url = baseUrl + '/content-view/ROOT';
    event.waitUntil(clients.matchAll({
        type: 'window'
    })
            .then(function (windowClients) {
                for (var i = 0; i < windowClients.length; i++) {
                    var client = windowClients[i];
                    if (client.url === url && 'focus' in client) {
                        return client.focus();
                    }
                }
                if (clients.openWindow) {
                    return clients.openWindow(url);
                }
            })
            .catch(function (error) {
                console.log('Notification click failed: ', error);
            }));
});