(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('historico-cotas', {
            parent: 'entity',
            url: '/historico-cotas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.historicoCotas.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historico-cotas/historico-cotas.html',
                    controller: 'HistoricoCotasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('historicoCotas');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('historico-cotas-detail', {
            parent: 'historico-cotas',
            url: '/historico-cotas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.historicoCotas.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historico-cotas/historico-cotas-detail.html',
                    controller: 'HistoricoCotasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('historicoCotas');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'HistoricoCotas', function($stateParams, HistoricoCotas) {
                    return HistoricoCotas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'historico-cotas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('historico-cotas-detail.edit', {
            parent: 'historico-cotas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historico-cotas/historico-cotas-dialog.html',
                    controller: 'HistoricoCotasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistoricoCotas', function(HistoricoCotas) {
                            return HistoricoCotas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historico-cotas.new', {
            parent: 'historico-cotas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historico-cotas/historico-cotas-dialog.html',
                    controller: 'HistoricoCotasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataCota: null,
                                vlrCota: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('historico-cotas', null, { reload: 'historico-cotas' });
                }, function() {
                    $state.go('historico-cotas');
                });
            }]
        })
        .state('historico-cotas.edit', {
            parent: 'historico-cotas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historico-cotas/historico-cotas-dialog.html',
                    controller: 'HistoricoCotasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistoricoCotas', function(HistoricoCotas) {
                            return HistoricoCotas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historico-cotas', null, { reload: 'historico-cotas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historico-cotas.delete', {
            parent: 'historico-cotas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historico-cotas/historico-cotas-delete-dialog.html',
                    controller: 'HistoricoCotasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HistoricoCotas', function(HistoricoCotas) {
                            return HistoricoCotas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historico-cotas', null, { reload: 'historico-cotas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
