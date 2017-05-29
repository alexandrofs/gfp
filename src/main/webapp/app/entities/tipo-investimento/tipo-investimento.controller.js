(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoController', TipoInvestimentoController);

    TipoInvestimentoController.$inject = ['$scope', '$state', 'TipoInvestimento', 'ModalidadeService'];

    function TipoInvestimentoController ($scope, $state, TipoInvestimento, ModalidadeService) {
        var vm = this;
        
        vm.modalidades = ModalidadeService.query();
        vm.tipoInvestimentos = [];

        loadAll();

        function loadAll() {
            TipoInvestimento.query(function(result) {
                vm.tipoInvestimentos = result;
            });
        }
    }
})();
