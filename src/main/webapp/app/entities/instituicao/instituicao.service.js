(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('Instituicao', Instituicao);

    Instituicao.$inject = ['$resource'];

    function Instituicao ($resource) {
        var resourceUrl =  'api/instituicaos/:id';

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
