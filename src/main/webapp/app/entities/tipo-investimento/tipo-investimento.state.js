(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-investimento', {
            parent: 'entity',
            url: '/tipo-investimento',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'gfpApp.tipoInvestimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-investimento/tipo-investimentos.html',
                    controller: 'TipoInvestimentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoInvestimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-investimento-detail', {
            parent: 'entity',
            url: '/tipo-investimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.tipoInvestimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-investimento/tipo-investimento-detail.html',
                    controller: 'TipoInvestimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoInvestimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoInvestimento', function($stateParams, TipoInvestimento) {
                    return TipoInvestimento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tipo-investimento.new', {
            parent: 'tipo-investimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-investimento/tipo-investimento-dialog.html',
                    controller: 'TipoInvestimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                modalidade: null,
                                tipoIndexador: null,
                                indice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-investimento', null, { reload: true });
                }, function() {
                    $state.go('tipo-investimento');
                });
            }]
        })
        .state('tipo-investimento.edit', {
            parent: 'tipo-investimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-investimento/tipo-investimento-dialog.html',
                    controller: 'TipoInvestimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoInvestimento', function(TipoInvestimento) {
                            return TipoInvestimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-investimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-investimento.delete', {
            parent: 'tipo-investimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-investimento/tipo-investimento-delete-dialog.html',
                    controller: 'TipoInvestimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoInvestimento', function(TipoInvestimento) {
                            return TipoInvestimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-investimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
