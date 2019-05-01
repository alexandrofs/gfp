(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('HistoricoCotasController', HistoricoCotasController);

    HistoricoCotasController.$inject = ['$scope', '$state', 'HistoricoCotas'];

    function HistoricoCotasController ($scope, $state, HistoricoCotas) {
        var vm = this;
        
        vm.historicoCotas = [];

        loadAll();

        function loadAll() {
            HistoricoCotas.query(function(result) {
                vm.historicoCotas = result;
            });
        }
    }
})();
