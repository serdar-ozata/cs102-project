/*
    This class is responsible for all-things mapbox.
    With-in this class we:
    - initialized the map
    - initialized the Draw function(s)
    - added map controls
    - added the search bar
    - set-up get & set Coordinates methods/functions (on mouse click, and screen move)
    - set-up retrieving center coordinates of the map
    - set-up function to record shape coordinates and send them to the databse
    - added function to draw incoming shapes using coordinates from database
    - removing draw features function
    - fetching tempreture related data (open weather API) and sending it to the map

*/

//properties
//var nav = new mapboxgl.NavigationControl();
var draw;
//var draw2;
var allShapesList =[];
var cords = [0,0];
var record = false;
var mousecrods = [];

//main method
function renderMapbox(cords, zoom) {

    /* Styles for map
    terrain: mapbox://styles/mapbox/outdoors-v11
    satellite-street: mapbox://styles/mapbox/satellite-streets-v11
    satellite: mapbox://styles/mapbox/satellite-v9
    cool: mapbox://styles/mapbox-map-design/ckhqrf2tz0dt119ny6azh975y
    frank: mapbox://styles/berkozkan/cko8k6hfk0e3718qkz35erd41
    */

    //initialize map
    map = new mapboxgl.Map ({
          container: 'map',
          style: 'mapbox://styles/mapbox/satellite-streets-v11',
          center: cords,
          zoom: zoom
     });
     Draw();

    //add navigation controls to map
    //map.addControl(nav, 'bottom-left');
    //-----Working------//

    //set up search bar, and other deatures on load.
    map.on('load', function(){

        //initialize search bar
        map.addControl(
            new MapboxGeocoder({
                accessToken: mapboxgl.accessToken,
                mapboxgl: mapboxgl
            })
        , 'top-left');

        //disable double mouse click zoom
        map.doubleClickZoom.disable();

   });
    //-----Working------//


    //shows center coordinates when moved, useful for OpenWeather API
    map.on('moveend', function centerCords(){
        var center = map.getCenter();
        getWeather();
        return center;
    });
    //-----Working------//

    //get coodinates on mouse click form mouse
    map.on('click', function clickCords(e){
        var lat = e.lngLat.lat;
        var lng = e.lngLat.lng;
        setCoordinates(lng, lat);
        return cords;
    });
    //-----Working------//


} //end of map renderer


//records the coordiantes of the newly drawn shapes using mouse click events
function recordShapeCords(){

        mousecrods = [];
        //record click into array
        map.on('click', function(){
            //check if should records coords
            if (record === true){
               point = getCoordiantes();
               //push array as string
               mousecrods.push("[" + point + "]");
            }
        });

};

//Save changes and send shape's coordiantes to database each time a new shape is created
function save(){

    //stops recording mouse coords
    record = false;

    //checks if mousecords array is empty
    if (mousecrods.length > 0){
                allShapesList = [];
                //pushes moursecords to another array
                allShapesList.push("{" + mousecrods + "}");
                mousecrods = [];
    }
    mousecrods = [];
};


//gts current coordiantes
function currentCords(e){
    var lat = e.lngLat.lat;
    var lng = e.lngLat.lng;
    setCoordinates(lng, lat);
}

//removes drawing tools and shapes
function removeDraw(){
     map.removeControl(draw);
}

//creates draw object
function Draw(){

    //checks if draw is null
    //if (draw == null){
        //initialize drawing
        draw = new MapboxDraw({

                controls: {
                    line_string: true,
                    combine_features: true,
                    uncombine_features: true,
                    point: true,
                    polygon: true,
                    trash: true
                },
                defaultMode: 'draw_polygon'

       });
        map.addControl(draw,'bottom-left');
        //-----Working------//
    //}

}



//add shape's to map using database info
function addShapes(id, cords){


    //Draw();

    //creates new feature for shape
    var feature = {
        'id': id,
        'userProperties': true,
        'type': 'Feature',
        'properties': {},
        'geometry': { 'type': 'Polygon', 'coordinates': [cords]}
    };
    //adds feature to draw
    draw.add(feature);

}




//Setter
function setCoordinates(lng, lat){
    cords = [lng,lat];
}
//getter
function getCoordiantes(){
    return cords;
}


//send coordinates to database
function returnCordsLists(){
    return ""+allShapesList;
}

//get
function getRecord(){
    return record;
}
//set
function recordToTrue(){
     record = true;
}

//get center coords
function getCenterCords(){
      var center = map.getCenter();
      return ""+center;
}
//gets zoom level
function getZoomLevel(){
     var zoom = map.getZoom();
     return ""+zoom;
}



/*

    the following part is calling the OpenWeatherApp API
    Simply put,
    The OWA API is called, which returns a complicated array.
    Through this array we can get all the information we need

*/

//declaring varibles for today, tomorrow, and the day after
var temp;
var description;
var humidity;

var temp2;
var tempMax2;
var tempMin2;
var description2;
var humidity2;

var temp3;
var tempMax3;
var tempMin3;
var description3;
var humidity3;

//gets weather data
function getWeather(x, y){

    var lat;
    var lng;

    if (x == null || y == null){
         lat = map.getCenter().lat;
         lng = map.getCenter().lng;
    } else {

        lat = x;
        lng = y;

    }
    //calls api
    fetch('https://api.openweathermap.org/data/2.5/onecall?lat=' + lat + '&lon='+lng+'&exclude=minutely,hourly,alerts&units=metric&appid=195742672f60b95c5978f038cba6a929')
    .then(responce => responce.json())
    .then(data => {

        //assigns variables to data
        var t1 = data['current']['temp'];
        setTemp(t1);
        var h1 = data['current']['humidity'];
        setHum(h1);
        var d1 = data['current']['weather'][0]['description'];
        setDescription(d1);

        var t2 = data['daily'][1]['temp']['day'];
        setTemp2(t2);
        var tMax2 = data['daily'][1]['temp']['max'];
        setTemp2Max(tMax2);
        var tMin2 = data['daily'][1]['temp']['min'];
        setTemp2Min(tMin2);
        var h2 = data['daily'][1]['humidity'];
        setHum2(h2);
        var d2 = data['daily'][1]['weather'][0]['description'];
        setDescription2(d2);

        var t3 = data['daily'][2]['temp']['day'];
        setTemp3(t3);
        var tMax3 = data['daily'][2]['temp']['max'];
        setTemp3Max(tMax3);
        var tMin3 = data['daily'][2]['temp']['min'];
        setTemp3Min(tMin3);
        var h3 = data['daily'][2]['humidity'];
        setHum3(h3);
        var d3 = data['daily'][2]['weather'][0]['description'];
        setDescription3(d3);

        /*
        console.log("temp: " + getTemp() + " hum: " + getHum() + " description " + getDescription());
        console.log("temp2: " + getTemp2() + " min: " + getTemp2Min() + " max: " + getTemp2Max() + " hum2: " + getHum2() + " description2 " + getDescription());
        console.log("temp3: " + getTemp3() + " min: " + getTemp3Min() + " max: " + getTemp3Max() + " hum3: " + getHum3() + " description3 " + getDescription());
        console.log(data);
        */
    });
}


//current data
//set
function setHum(data){
    humidity = data;
}
function setTemp(data){
    temp = data;
}
function setDescription(data){
    description = data;
}
//get
function getHum(){
    return humidity;
}
function getTemp(){
    return temp;
}
function getDescription(){
    return description;
}

//tomorrow's data
//set
function setHum2(data){
    humidity2 = data;
}
function setTemp2(data){
    temp2 = data;
}
function setTemp2Max(data){
    tempMax2 = data;
}
function setTemp2Min(data){
    tempMin2 = data;
}
function setDescription2(data){
    description2 = data;
}
//get
function getHum2(){
    return humidity2;
}
function getTemp2(){
    return temp2;
}
function getTemp2Max(){
     return tempMax2;
}
function getTemp2Min(){
    return tempMin2;
}
function getDescription2(){
    return description2;
}

//the day after tomorrow's data
//set
function setHum3(data){
    humidity3 = data;
}
function setTemp3(data){
    temp3 = data;
}
function setTemp3Max(data){
    tempMax3 = data;
}
function setTemp3Min(data){
    tempMin3 = data;
}
function setDescription3(data){
    description3 = data;
}
//get
function getHum3(){
    return humidity3;
}
function getTemp3(){
    return temp3;
}
function getTemp3Max(){
     return tempMax3;
}
function getTemp3Min(){
    return tempMin3;
}
function getDescription3(){
    return description3;
}
