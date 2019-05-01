(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('TabelaImpostoRenda', TabelaImpostoRenda);

    TabelaImpostoRenda.$inject = ['$resource'];

    function TabelaImpostoRenda ($resource) {
        var resourceUrl =  'api/tabela-imposto-rendas/:id';

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
