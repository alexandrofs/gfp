(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('HistoricoCotas', HistoricoCotas);

    HistoricoCotas.$inject = ['$resource', 'DateUtils'];

    function HistoricoCotas ($resource, DateUtils) {
        var resourceUrl =  'api/historico-cotas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataCota = DateUtils.convertLocalDateFromServer(data.dataCota);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataCota = DateUtils.convertLocalDateToServer(copy.dataCota);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataCota = DateUtils.convertLocalDateToServer(copy.dataCota);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
