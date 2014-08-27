<?php
$client= new GearmanClient();
$client->addServer();
print $client->do("title", "AlL THE World's a sTagE");
print "\n";
?>