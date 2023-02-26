mapboxgl.accessToken = 'pk.eyJ1IjoiYmVya296a2FuIiwiYSI6ImNrbDNvMTN6MzA2YmQybm13dGprc24zNTIifQ.q-OZoPBNQkIgsF1R7arHkg';

var coordinates = [34.99574887457743, 39.23425287174976]



function setCoordinates(lng, lat){
    coordinates = [lng,lat];

}

const map = new mapboxgl.Map({
       container: 'map',
       style: 'mapbox://styles/mapbox/satellite-streets-v11?optimize=true',
       center: coordinates,
       zoom: 5

});

//shows center when moved, useful for OpenWeather API
map.on('moveend', function(){

    var center = map.getCenter();
    document.getElementById("console").innerHTML += "Current Center: " + center + "\n";
    document.getElementById("console").scrollTop = document.getElementById("console").scrollHeight;


})

 var customData = {
 'features': [
 {'type': 'Feature',
  'properties':
    {'title':
    'Anils Farm'},
  'geometry': {
    'coordinates': [32.2314, 34.2341],'type': 'Point'
    }},
//new points
 {
 'type': 'Feature',
 'properties': {
 'title': 'Emirhans House'
 },
 'geometry': {
 'coordinates': [32.65784, 34.3425],
 'type': 'Point'
 }
 },
 {
 'type': 'Feature',
 'properties': {
 'title': 'Serdars Work'
 },
 'geometry': {
 'coordinates': [32.657123484, 34.34321425],
 'type': 'Point'
 }
 }
 ],
 'type': 'FeatureCollection'
 };

//needed for serach
function forwardGeocoder(query) {
    var matchingFeatures = [];
    for (var i = 0; i < customData.features.length; i++) {
    var feature = customData.features[i];
    // Handle queries with different capitalization
    // than the source data by calling toLowerCase().
    if (
    feature.properties.title
    .toLowerCase()
    .search(query.toLowerCase()) !== -1
    ) {
    // Add a tree emoji as a prefix for custom
    // data results using carmen geojson format:
    // https://github.com/mapbox/carmen/blob/master/carmen-geojson.md
    feature['place_name'] = '🏠  ' + feature.properties.title;
    feature['center'] = feature.geometry.coordinates;
    feature['place_type'] = ['building'];
    matchingFeatures.push(feature);
    }
    }
    return matchingFeatures;
}

//plus minus controls on map
var nav = new mapboxgl.NavigationControl();
map.addControl(nav);

// Add the control/serach bar to the map.
map.addControl(
    new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        localGeocoder: forwardGeocoder,
        zoom: 14,
        placeholder: 'Search',
        mapboxgl: mapboxgl
    })
, 'top-left');




//get coords form mouse
map.on('click', function(e){
    document.getElementById("console").innerHTML += "Click pos: " + e.lngLat + "\n";
    document.getElementById("console").scrollTop = document.getElementById("console").scrollHeight;
});





/*
map.addControl(new mapboxgl.GeolocateControl({
  positionOptions: {
    enableHighAccuracy: true
  },
    trackUserLocation: true
}));

*/




