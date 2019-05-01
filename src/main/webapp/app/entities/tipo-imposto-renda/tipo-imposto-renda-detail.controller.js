(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoImpostoRendaDetailController', TipoImpostoRendaDetailController);

    TipoImpostoRendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TipoImpostoRenda', 'TabelaImpostoRenda'];

    function TipoImpostoRendaDetailController($scope, $rootScope, $stateParams, entity, TipoImpostoRenda, TabelaImpostoRenda) {
        var vm = this;

        vm.tipoImpostoRenda = entity;

        var unsubscribe = $rootScope.$on('gfpApp:tipoImpostoRendaUpdate', function(event, result) {
            vm.tipoImpostoRenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
