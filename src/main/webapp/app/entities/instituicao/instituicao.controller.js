(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InstituicaoController', InstituicaoController);

    InstituicaoController.$inject = ['$scope', '$state', 'Instituicao'];

    function InstituicaoController ($scope, $state, Instituicao) {
        var vm = this;

        vm.instituicaos = [];

        loadAll();

        function loadAll() {
            Instituicao.query(function(result) {
                vm.instituicaos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
