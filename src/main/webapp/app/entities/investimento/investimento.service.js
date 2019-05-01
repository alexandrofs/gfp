(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('Investimento', Investimento);

    Investimento.$inject = ['$resource', 'DateUtils'];

    function Investimento ($resource, DateUtils) {
        var resourceUrl =  'api/investimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataAplicacao = DateUtils.convertLocalDateFromServer(data.dataAplicacao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dataAplicacao = DateUtils.convertLocalDateToServer(data.dataAplicacao);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dataAplicacao = DateUtils.convertLocalDateToServer(data.dataAplicacao);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
