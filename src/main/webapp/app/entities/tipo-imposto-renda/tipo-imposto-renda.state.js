(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-imposto-renda', {
            parent: 'entity',
            url: '/tipo-imposto-renda',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'gfpApp.tipoImpostoRenda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-imposto-renda/tipo-imposto-rendas.html',
                    controller: 'TipoImpostoRendaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoImpostoRenda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-imposto-renda-detail', {
            parent: 'entity',
            url: '/tipo-imposto-renda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.tipoImpostoRenda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-imposto-renda/tipo-imposto-renda-detail.html',
                    controller: 'TipoImpostoRendaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoImpostoRenda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoImpostoRenda', function($stateParams, TipoImpostoRenda) {
                    return TipoImpostoRenda.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tipo-imposto-renda.new', {
            parent: 'tipo-imposto-renda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-imposto-renda/tipo-imposto-renda-dialog.html',
                    controller: 'TipoImpostoRendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-imposto-renda', null, { reload: true });
                }, function() {
                    $state.go('tipo-imposto-renda');
                });
            }]
        })
        .state('tipo-imposto-renda.edit', {
            parent: 'tipo-imposto-renda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-imposto-renda/tipo-imposto-renda-dialog.html',
                    controller: 'TipoImpostoRendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoImpostoRenda', function(TipoImpostoRenda) {
                            return TipoImpostoRenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-imposto-renda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-imposto-renda.delete', {
            parent: 'tipo-imposto-renda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-imposto-renda/tipo-imposto-renda-delete-dialog.html',
                    controller: 'TipoImpostoRendaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoImpostoRenda', function(TipoImpostoRenda) {
                            return TipoImpostoRenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-imposto-renda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
