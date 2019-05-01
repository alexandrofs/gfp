(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tabela-imposto-renda', {
            parent: 'entity',
            url: '/tabela-imposto-renda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.tabelaImpostoRenda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-rendas.html',
                    controller: 'TabelaImpostoRendaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tabelaImpostoRenda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tabela-imposto-renda-detail', {
            parent: 'entity',
            url: '/tabela-imposto-renda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.tabelaImpostoRenda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-renda-detail.html',
                    controller: 'TabelaImpostoRendaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tabelaImpostoRenda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TabelaImpostoRenda', function($stateParams, TabelaImpostoRenda) {
                    return TabelaImpostoRenda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tabela-imposto-renda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tabela-imposto-renda-detail.edit', {
            parent: 'tabela-imposto-renda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-renda-dialog.html',
                    controller: 'TabelaImpostoRendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TabelaImpostoRenda', function(TabelaImpostoRenda) {
                            return TabelaImpostoRenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabela-imposto-renda.new', {
            parent: 'tabela-imposto-renda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-renda-dialog.html',
                    controller: 'TabelaImpostoRendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numDias: null,
                                pctAliquota: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tabela-imposto-renda', null, { reload: 'tabela-imposto-renda' });
                }, function() {
                    $state.go('tabela-imposto-renda');
                });
            }]
        })
        .state('tabela-imposto-renda.edit', {
            parent: 'tabela-imposto-renda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-renda-dialog.html',
                    controller: 'TabelaImpostoRendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TabelaImpostoRenda', function(TabelaImpostoRenda) {
                            return TabelaImpostoRenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabela-imposto-renda', null, { reload: 'tabela-imposto-renda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabela-imposto-renda.delete', {
            parent: 'tabela-imposto-renda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabela-imposto-renda/tabela-imposto-renda-delete-dialog.html',
                    controller: 'TabelaImpostoRendaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TabelaImpostoRenda', function(TabelaImpostoRenda) {
                            return TabelaImpostoRenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabela-imposto-renda', null, { reload: 'tabela-imposto-renda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
