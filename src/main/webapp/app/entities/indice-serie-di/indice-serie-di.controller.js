(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiController', IndiceSerieDiController);

    IndiceSerieDiController.$inject = ['$scope', '$state', 'IndiceSerieDi', 'ParseLinks', 'AlertService'];

    function IndiceSerieDiController ($scope, $state, IndiceSerieDi, ParseLinks, AlertService) {
        var vm = this;
        
        vm.indiceSerieDis = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.predicate = 'data';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            IndiceSerieDi.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'data') {
                    result.push('data');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.indiceSerieDis.push(data[i]);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.indiceSerieDis = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();