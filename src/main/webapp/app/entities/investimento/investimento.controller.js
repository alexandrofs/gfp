(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InvestimentoController', InvestimentoController);

    InvestimentoController.$inject = ['$scope', '$state', 'Investimento'];

    function InvestimentoController ($scope, $state, Investimento) {
        var vm = this;
        
        vm.investimentos = [];

        loadAll();

        function loadAll() {
            Investimento.query(function(result) {
                vm.investimentos = result;
            });
        }
    }
})();
