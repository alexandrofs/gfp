(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoImpostoRendaController', TipoImpostoRendaController);

    TipoImpostoRendaController.$inject = ['$scope', '$state', 'TipoImpostoRenda'];

    function TipoImpostoRendaController ($scope, $state, TipoImpostoRenda) {
        var vm = this;

        vm.tipoImpostoRendas = [];

        loadAll();

        function loadAll() {
            TipoImpostoRenda.query(function(result) {
                vm.tipoImpostoRendas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
