(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('IndiceSerieDi', IndiceSerieDi);

    IndiceSerieDi.$inject = ['$resource', 'DateUtils'];

    function IndiceSerieDi ($resource, DateUtils) {
        var resourceUrl =  'api/indice-serie-dis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data = DateUtils.convertLocalDateFromServer(data.data);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.data = DateUtils.convertLocalDateToServer(data.data);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.data = DateUtils.convertLocalDateToServer(data.data);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
