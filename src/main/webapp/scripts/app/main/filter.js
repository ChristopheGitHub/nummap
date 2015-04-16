/**
 * Created by eisti on 4/8/15.
 */
'use strict';


angular.module('nummapApp').filter('capitalize', function(){
    return function(input) {
        return input.substring(0,1).toUpperCase()+input.substring(1).toLowerCase();
    };
});

angular.module('nummapApp').filter('categories', function() {

	return function (input, categories) {
		var res = [];

		angular.forEach(input, function (marker){
			if(categories.indexOf(marker.category) !== -1) {	
				res.push(marker);
			}
		});

		return res;
	};
});

angular.module('nummapApp').filter('fields', function() {

	return function (input, fields) {
		var res = [];

		angular.forEach(input, function (marker){
			var keepGoing = true;
			angular.forEach(marker.fields, function (field) {
				if(fields.indexOf(field) !== -1 && keepGoing) {
					res.push(marker);
					keepGoing = false;
				}
			});
		});
		console.log(res);
		return res;
	};
});