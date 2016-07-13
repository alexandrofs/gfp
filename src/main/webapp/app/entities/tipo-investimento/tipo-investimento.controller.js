(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoController', TipoInvestimentoController);

    TipoInvestimentoController.$inject = ['$scope', '$state', 'TipoInvestimento'];

    function TipoInvestimentoController ($scope, $state, TipoInvestimento) {
        var vm = this;
        
        vm.tipoInvestimentos = [];

        loadAll();

        function loadAll() {
            TipoInvestimento.query(function(result) {
                vm.tipoInvestimentos = result;
            });
        }
    }
})();
