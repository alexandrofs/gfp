(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoImpostoRendaDetailController', TipoImpostoRendaDetailController);

    TipoImpostoRendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoImpostoRenda', 'TabelaImpostoRenda'];

    function TipoImpostoRendaDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoImpostoRenda, TabelaImpostoRenda) {
        var vm = this;

        vm.tipoImpostoRenda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:tipoImpostoRendaUpdate', function(event, result) {
            vm.tipoImpostoRenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
