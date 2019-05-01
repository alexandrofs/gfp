(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('HistoricoCotasDetailController', HistoricoCotasDetailController);

    HistoricoCotasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'HistoricoCotas', 'Investimento'];

    function HistoricoCotasDetailController($scope, $rootScope, $stateParams, entity, HistoricoCotas, Investimento) {
        var vm = this;

        vm.historicoCotas = entity;

        var unsubscribe = $rootScope.$on('gfpApp:historicoCotasUpdate', function(event, result) {
            vm.historicoCotas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
