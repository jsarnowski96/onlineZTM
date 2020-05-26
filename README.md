# onlineZTM
Android app parsing JSON from ZTM open data bank and rendering bus stops markers on Google Maps.

## Core components
- `LoginActivity` - handles user's authentication and acts as launcher activity
- `UserRegistrationActivity` - provides interface for user registration
- `MainActivity` - core activity allowing user to select desirable action he/she wants to perform
- `TimetableActivity` - parses JSONArray into ArrayList<String> object which is loaded into drop down list (Spinner)
- `MapsActivity` - initial project's activity parsing JSON and rendering markers based on bus stop's coordinates within Tricity's range

## TODO
- [ ] `TimetableActivity->busStopsSpinner`
- [ ] `MapsActivity` - extend basic markers with additional actions/set of information regarding current bus stop's coordinates
- [ ] general optimization of JSON parsing process - one of the issues is related to `TimetableActivity` parsing same JSON input every time when user leaves the activity and re-opens it, since BusStop objects aren't stored in sharedPreferences (at least not yet)
- [ ] - minor code refactoring & cleanup


## Screenshots

