/**
 * Created by eisti on 3/24/15.
 */
'use strict';

angular.module('nummapApp')
    .factory('Domains', function ($resource) {
        return $resource('api/domains/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });

