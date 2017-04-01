(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TabelaImpostoRendaDetailController', TabelaImpostoRendaDetailController);

    TabelaImpostoRendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TabelaImpostoRenda', 'TipoImpostoRenda'];

    function TabelaImpostoRendaDetailController($scope, $rootScope, $stateParams, entity, TabelaImpostoRenda, TipoImpostoRenda) {
        var vm = this;

        vm.tabelaImpostoRenda = entity;

        var unsubscribe = $rootScope.$on('gfpApp:tabelaImpostoRendaUpdate', function(event, result) {
            vm.tabelaImpostoRenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
