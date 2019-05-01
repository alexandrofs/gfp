(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('TipoImpostoRenda', TipoImpostoRenda);

    TipoImpostoRenda.$inject = ['$resource'];

    function TipoImpostoRenda ($resource) {
        var resourceUrl =  'api/tipo-imposto-rendas/:id';

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
