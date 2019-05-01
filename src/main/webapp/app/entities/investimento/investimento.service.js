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
                    var copy = angular.copy(data);
                    copy.dataAplicacao = DateUtils.convertLocalDateToServer(copy.dataAplicacao);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataAplicacao = DateUtils.convertLocalDateToServer(copy.dataAplicacao);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
