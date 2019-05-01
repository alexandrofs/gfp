(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TabelaImpostoRendaController', TabelaImpostoRendaController);

    TabelaImpostoRendaController.$inject = ['$scope', '$state', 'TabelaImpostoRenda'];

    function TabelaImpostoRendaController ($scope, $state, TabelaImpostoRenda) {
        var vm = this;

        vm.tabelaImpostoRendas = [];

        loadAll();

        function loadAll() {
            TabelaImpostoRenda.query(function(result) {
                vm.tabelaImpostoRendas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
