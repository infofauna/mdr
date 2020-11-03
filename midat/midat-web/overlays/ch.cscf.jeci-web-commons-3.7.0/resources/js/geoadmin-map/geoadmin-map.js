var module = angular.module('geoadminMap', [])

.directive('geoadminMap', ["olStylesFactory", function(olStylesFactory){

    //we generate a unique random id for the element so that we can have several maps on same page
    //var mapElementId = "geoadminmap-"+Math.floor(Math.random() * 10000000);
    var mapElementId = "geoadmin-map-container";

    var map = null;
    var markerLayer = null;

    function addMarkerAt(coord){

        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(coord)
        });

        iconFeature.setStyle(olStylesFactory.anchorIconStyle());

        var vectorSource = new ol.source.Vector({
            features: [iconFeature]
        });

        var vectorLayer = new ol.layer.Vector({
            source: vectorSource
        });

        if(markerLayer){
            map.removeLayer(markerLayer);
        }

        map.addLayer(vectorLayer);

        markerLayer = vectorLayer;
    }


    return {
        restrict: "E",
        restrict: "E",
        scope: {
            model : "="  //the parameters are passed via this model object, and the map object and other properties are exposed through it
        },
        replace: true,
        templateUrl : JECI.appRoot+"/resources/js/geoadmin-map/template.html",

        link : function(scope, element){

            scope.mapElementId = mapElementId;

            var layer = ga.layer.create('ch.swisstopo.pixelkarte-farbe');
            var layers = [layer];

            /*
            if(scope.model.additionalLayers){
                for(var i = 0; i<scope.model.additionalLayers.length; i++){
                    layers.push(scope.model.additionalLayers[i]);
                }
            }*/

            map = new ga.Map({
                target: mapElementId,
                layers: layers,
                view: new ol.View2D({
                    resolution: scope.model.resolution,
                    center: [660158, 183641]
                })
            });

            //add interactions (e.g. Select for features)
            if(scope.model.interactions){
                var arrayLength = scope.model.interactions.length;
                for(var i=0; i<arrayLength; i++){
                    map.addInteraction(scope.model.interactions[i]);
                }
            }

            scope.model.map = map;

            //bind map center to model bi-directional
            map.on('moveend', function(){
                scope.model.center = map.getView().getCenter();
            });

            var centerMarkerAlreadyAdded=false;

            scope.$watch("model.center", function(){

                //force the map to redraw when the size is set
                //there must be a better place to hook this
                map.updateSize();

                if(scope.model.center) {
                    map.getView().setCenter(scope.model.center);
                    if (scope.model.showMarkerAtCenter && !centerMarkerAlreadyAdded) {
                        addMarkerAt(scope.model.center);
                        centerMarkerAlreadyAdded = true;
                    }
                }
            });

            var previouslyAddedLayers = [];

            scope.$watchCollection("model.additionalLayers", function(){

                //remove previously added layers that are not present any more
                for (var i = 0; i < previouslyAddedLayers.length; i++) {

                    var l = previouslyAddedLayers[i];

                    if(scope.model.additionalLayers.indexOf(l) == -1){
                        map.getLayers().remove(l);
                    }
                }

                //add layers that were not previously present
                if(scope.model.additionalLayers){
                    for(var i = 0; i<scope.model.additionalLayers.length; i++){

                        var l = scope.model.additionalLayers[i];

                        if(!previouslyAddedLayers || previouslyAddedLayers.indexOf(l) == -1){
                            map.getLayers().push(scope.model.additionalLayers[i]);
                        }
                    }


                    //store added layers for future removal, as copy of the array
                    previouslyAddedLayers = scope.model.additionalLayers.slice(0);

                }
            });


            //bind map resolution to model bi-directional
            scope.$watch("model.resolution", function(){
                if(scope.model.resolution){
                    map.getView().setResolution(scope.model.resolution);
                }
            });

            map.getView().on("change:resolution", function(){
                scope.model.resolution = map.getView().getResolution();
            });

            //Bind the coordinates display to mouse events
            if(scope.model.coordinatesDisplay){
                scope.model.coordinatesDisplay = {
                    show : true,
                    label : "Coordinates :"
                };

                var coordinatesDisplay = scope.model.coordinatesDisplay;


                //coord display appears when mouse is over the map
                element
                    .on("mouseenter", function () {
                        scope.$apply(function(){
                            coordinatesDisplay.show = true;
                        });

                    })
                    .on("mouseleave", function () {
                        scope.$apply(function(){
                            coordinatesDisplay.show = false;
                        });
                    });

                //update the coord display when mouse moves
                map.on("pointermove", function (event) {
                    scope.$apply(function(){
                        coordinatesDisplay.x = Math.round(event.coordinate[0]);
                        coordinatesDisplay.y = Math.round(event.coordinate[1]);
                    });
                });
            }


            //change the cursor to pointer when the mouse hovers on feature
            if(scope.model.changeCursorHoverFeature){

                var cursorHoverStyle = scope.model.changeCursorHoverFeature;
                var target = map.getTarget();

                //target returned might be the DOM element or the ID of this element dependeing on how the map was initialized
                //either way get a jQuery object for it
                var jTarget = typeof target === "string" ? $("#"+target) : $(target);

                map.on("pointermove", function (event) {
                    var mouseCoordInMapPixels = [event.originalEvent.offsetX, event.originalEvent.offsetY];

                    //detect feature at mouse coords
                    var hit = map.forEachFeatureAtPixel(mouseCoordInMapPixels, function (feature, layer) {
                        return true;
                    });

                    if (hit) {
                        jTarget.css("cursor", cursorHoverStyle);
                    } else {
                        jTarget.css("cursor", "");
                    }
                });
            }


            //add extra event handlers from the model to the map
            if(scope.model.mapEventHandlers && scope.model.mapEventHandlers.length >0){
                var arrayLength = scope.model.mapEventHandlers.length;
                for(var i=0; i<arrayLength; i++){
                    var handlerToAdd = scope.model.mapEventHandlers[i];
                    map.on(handlerToAdd.event, handlerToAdd.handler);
                }
            }

            //Add the rectangle selection interaction, exposing selected bounds in the model
            if(scope.model.rectangleSelector){

                var rectangleSelector = scope.model.rectangleSelector;



                rectangleSelector.selectionMode = "off";

                var dragBox = new ol.interaction.DragBox({
                    style: olStylesFactory.selectionBoxStyle
                });

                var selectionBoxOverlay = new ol.FeatureOverlay({
                    map: map,
                    style: function(feature, resolution) {
                        return [olStylesFactory.selectionBoxStyle];
                    }
                });

                //if the model contains an active selector, add the geometry
                if(rectangleSelector.active){
                    selectionBoxOverlay.addFeature(new ol.Feature(rectangleSelector.geometry));
                }

                scope.model.rectangleSelector.clearSelection = function(){
                    selectionBoxOverlay.getFeatures().clear();
                    rectangleSelector.active = false;
                    rectangleSelector.rectangle = {};
                };

                // Listeners dragbox interaction event
                dragBox.on('boxstart', function(evt) {});

                dragBox.on('boxend', function(evt) {
                    scope.$apply(function(){
                        var bbox = dragBox.getGeometry().getExtent();

                        rectangleSelector.rectangle.west = Math.round(bbox[0]);
                        rectangleSelector.rectangle.east = Math.round(bbox[2]);

                        rectangleSelector.rectangle.north = Math.round(bbox[3]);
                        rectangleSelector.rectangle.south = Math.round(bbox[1]);

                        rectangleSelector.active = true;

                        selectionBoxOverlay.addFeature(new ol.Feature(dragBox.getGeometry()));
                        rectangleSelector.geometry = dragBox.getGeometry();
                        rectangleSelector.selectionMode = "off";

                        if(scope.model.onRectangleSelected){
                            scope.model.onRectangleSelected();
                        }
                    });
                });

                scope.$watch("model.rectangleSelector.selectionMode", function(){
                    if(rectangleSelector.selectionMode=="on"){
                        log("Selection mode on")
                        rectangleSelector.clearSelection();
                        element.addClass("drawing");
                        map.addInteraction(dragBox);
                    }else{
                        log("Selection mode off");
                        element.removeClass("drawing");
                        if(map){
                            map.removeInteraction(dragBox);
                        }
                    }
                });
            }
        }
    };

}]);


//////////////////// OpenLayers Styles Factory ///////////////////////////////////////

module.factory("olStylesFactory", [function(){

    var defaultMarkerIconStyle = {
        anchor: [0.5, 0.95], //the actual point is at the bottom center of the icon
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 1,
        src: JECI.appRoot+'/resources/images/icons/map-marker-48.png'
    };

    var selectionBoxStyle =  new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(80, 118, 141, 0.4)'
        }),
        stroke: new ol.style.Stroke({
            color: '#044E7D',
            width:2
        })
    });

    var featurePointStyleSmall = new ol.style.Circle({
        radius: 6,
        fill: new ol.style.Fill({color: "red"}),
        stroke: new ol.style.Stroke({color: "white", width: 3})
    });

    var featurePointStyleBig = new ol.style.Circle({
        radius: 10,
        fill: new ol.style.Fill({color: "red"}),
        stroke: new ol.style.Stroke({color: "white", width: 4})
    });

    var featurePointStyleSmallSelected = new ol.style.Circle({
        radius: 6,
        fill: new ol.style.Fill({color: "red"}),
        stroke: new ol.style.Stroke({color: "#0AFFF3", width: 3})
    });

    var featurePointStyleBigSelected = new ol.style.Circle({
        radius: 10,
        fill: new ol.style.Fill({color: "red"}),
        stroke: new ol.style.Stroke({color: "#0AFFF3", width: 4})
    });


    var featureTextStyleCache = {};
    var featureTextStyleSelectedCache = {};

    return {

        anchorIconStyle : function(){
            return new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ (defaultMarkerIconStyle))
            });
        },

        featureTextStyle : function(text){
            if(!featureTextStyleCache[text]){
                featureTextStyleCache[text] = new ol.style.Text({
                    font: 'bold 16px Helvetica,Arial,sans-serif',
                    text: text,
                    fill: new ol.style.Fill({
                        color: '#f00'
                    }),
                    stroke: new ol.style.Stroke({
                        color: '#fff',
                        width: 6
                    }),
                    offsetY : 18
                });
            }
            return featureTextStyleCache[text];
        },

        featureTextStyleSelected : function(text){
            if(!featureTextStyleSelectedCache[text]){
                featureTextStyleSelectedCache[text] = new ol.style.Text({
                    font: 'bold 16px Helvetica,Arial,sans-serif',
                    text: text,
                    fill: new ol.style.Fill({
                        color: '#f00'
                    }),
                    stroke: new ol.style.Stroke({
                        color: "#0AFFF3",
                        width: 6
                    }),
                    offsetY : 18
                });
            }
            return featureTextStyleSelectedCache[text];
        },

        selectionBoxStyle : selectionBoxStyle,
        featurePointStyleSmall : featurePointStyleSmall,
        featurePointStyleSmallSelected : featurePointStyleSmallSelected,
        featurePointStyleBig : featurePointStyleBig,
        featurePointStyleBigSelected : featurePointStyleBigSelected

    };

}]);
