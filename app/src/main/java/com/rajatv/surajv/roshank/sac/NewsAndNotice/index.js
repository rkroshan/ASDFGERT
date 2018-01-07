// imports firebase-functions module
const functions = require('firebase-functions');
// imports firebase-admin module
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

/* Listens for new messages added to /messages/:pushId and sends a notification to subscribed users */
exports.pushNotification = functions.database.ref('/updates/{pushId}').onWrite( event => {
/* Grab the current value of what was written to the Realtime Database */
    var valueObject = event.data.val();
    console.log('Push notification event triggered '+valueObject.heading);
/* Create a notification and data payload. They contain the notification information, and message to be sent respectively */ 
    const payload = {
        data: {
            title: valueObject.heading,
            message: valueObject.news
        }
    };
/* Create an options object that contains the time to live for the notification and the priority. */
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };
return admin.messaging().sendToTopic("notifications", payload, options);
});