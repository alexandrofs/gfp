(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('Carteira', Carteira);

    Carteira.$inject = ['$resource'];

    function Carteira ($resource) {
        var resourceUrl =  'api/carteiras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
