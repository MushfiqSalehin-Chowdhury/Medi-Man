<?php
// Require the bundled autoload file - the path may need to change
// based on where you downloaded and unzipped the SDK
require __DIR__ . '/twilio-php-master/Twilio/autoload.php';

// Use the REST API Client to make requests to the Twilio REST API
use Twilio\Rest\Client;

// Your Account SID and Auth Token from twilio.com/console
$sid = 'ACb240dc7bfc7d4058a1800166010c9028';
$token = '9d99d1c14ab3508fce7b7f5915c0a682';
$client = new Client($sid, $token);

// Use the client to do fun stuff like send text messages!
$client->messages->create(
    // the number you'd like to send the message to
    '+8801956154440',
    array(
        // A Twilio phone number you purchased at twilio.com/console
        'from' => '+17608535089',
        // the body of the text message you'd like to send
        'body' => "Your Code Number is: 0955"
    )
);