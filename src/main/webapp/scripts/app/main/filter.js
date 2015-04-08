/**
 * Created by eisti on 4/8/15.
 */

angular.module('nummapApp').filter('capitalize', function(){
    return function(input) {
        return input.substring(0,1).toUpperCase()+input.substring(1).toLowerCase();
    }
});
