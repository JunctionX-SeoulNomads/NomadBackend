# NomadBackend
Web backend repository


APIs

For tracing finding and saving disabled people cluster
* POST https://safe-nomad.herokuapp.com/user
{"userId":"4235","longitude":1111.2222,"latitude":3333.4444}

For getting realtime statistic about neighborhood 
* POST https://safe-nomad.herokuapp.com/driver
{"longitude":23.46,"latitude":4544.4444}

Before parking, for getting monthly statistic about neighborhood 
* POST https://safe-nomad.herokuapp.com/parking
{"longitude":23.46,"latitude":4544.4444}

For sending the complains about locations
* POST https://safe-nomad.herokuapp.com/complain
{"userId":"1253","message":"bad parking","longitude":100.1,"latitude":14.34}
