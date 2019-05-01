(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('CarteiraController', CarteiraController);

    CarteiraController.$inject = ['$scope', '$state', 'Carteira'];

    function CarteiraController ($scope, $state, Carteira) {
        var vm = this;

        vm.carteiras = [];

        loadAll();

        function loadAll() {
            Carteira.query(function(result) {
                vm.carteiras = result;
                vm.searchQuery = null;
            });
        }
    }
})();
