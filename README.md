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
- [ ] minor code refactoring & cleanup

## Known issues
Some virtual machines have some issues with the application, thus some of its feature may not work as intended - launching onlineZTM on physical device is the most reliable way of testing it.

## Screenshots

### Login Screen
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/Screenshot_1590530910.png" height="400" width="250" />

### User Registration Activity
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/Screenshot_1590530916.png" height="400" width="250" />

### Main Activity after successfull login
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/Screenshot_1590530979.png" height="400" width="250" />

### Google Maps Activity
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/Screenshot_1590530914.png" height="400" width="250"/>

### Timetable Activity
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/Screenshot_1590530934.png" height="400" width="250"/>

### Screen capture
<img src="https://github.com/jsarnowski96/onlineZTM/blob/master/screenshots/untitled.gif" height="400" width="250" />
