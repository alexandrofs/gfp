(function() {
    'use strict';
    angular
        .module('gfpApp')
        .factory('ModalidadeService', ModalidadeService);

    ModalidadeService.$inject = ['$resource'];

    function ModalidadeService ($resource) {
        var resourceUrl =  'api/modalidades';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false}
        });
    }
})();
