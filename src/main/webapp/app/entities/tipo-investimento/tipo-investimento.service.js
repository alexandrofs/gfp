(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('TipoInvestimento', TipoInvestimento);

    TipoInvestimento.$inject = ['$resource'];

    function TipoInvestimento ($resource) {
        var resourceUrl =  'api/tipo-investimentos/:id';

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
