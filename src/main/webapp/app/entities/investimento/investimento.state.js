(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('investimento', {
            parent: 'entity',
            url: '/investimento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.investimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investimento/investimentos.html',
                    controller: 'InvestimentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('investimento-detail', {
            parent: 'investimento',
            url: '/investimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.investimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investimento/investimento-detail.html',
                    controller: 'InvestimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Investimento', function($stateParams, Investimento) {
                    return Investimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'investimento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('investimento-detail.edit', {
            parent: 'investimento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investimento/investimento-dialog.html',
                    controller: 'InvestimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investimento', function(Investimento) {
                            return Investimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investimento.new', {
            parent: 'investimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investimento/investimento-dialog.html',
                    controller: 'InvestimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                dataAplicacao: null,
                                qtdeCota: null,
                                vlrCota: null,
                                pctPrePosFixado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('investimento', null, { reload: 'investimento' });
                }, function() {
                    $state.go('investimento');
                });
            }]
        })
        .state('investimento.edit', {
            parent: 'investimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investimento/investimento-dialog.html',
                    controller: 'InvestimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investimento', function(Investimento) {
                            return Investimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investimento', null, { reload: 'investimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investimento.delete', {
            parent: 'investimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investimento/investimento-delete-dialog.html',
                    controller: 'InvestimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Investimento', function(Investimento) {
                            return Investimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investimento', null, { reload: 'investimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
