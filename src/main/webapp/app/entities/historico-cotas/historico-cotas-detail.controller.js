(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('HistoricoCotasDetailController', HistoricoCotasDetailController);

    HistoricoCotasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HistoricoCotas', 'Investimento'];

    function HistoricoCotasDetailController($scope, $rootScope, $stateParams, previousState, entity, HistoricoCotas, Investimento) {
        var vm = this;

        vm.historicoCotas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:historicoCotasUpdate', function(event, result) {
            vm.historicoCotas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
