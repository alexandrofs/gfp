(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TabelaImpostoRendaDetailController', TabelaImpostoRendaDetailController);

    TabelaImpostoRendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TabelaImpostoRenda', 'TipoImpostoRenda'];

    function TabelaImpostoRendaDetailController($scope, $rootScope, $stateParams, previousState, entity, TabelaImpostoRenda, TipoImpostoRenda) {
        var vm = this;

        vm.tabelaImpostoRenda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:tabelaImpostoRendaUpdate', function(event, result) {
            vm.tabelaImpostoRenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
