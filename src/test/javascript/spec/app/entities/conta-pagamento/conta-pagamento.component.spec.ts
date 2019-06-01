/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { ContaPagamentoComponent } from 'app/entities/conta-pagamento/conta-pagamento.component';
import { ContaPagamentoService } from 'app/entities/conta-pagamento/conta-pagamento.service';
import { ContaPagamento } from 'app/shared/model/conta-pagamento.model';

describe('Component Tests', () => {
    describe('ContaPagamento Management Component', () => {
        let comp: ContaPagamentoComponent;
        let fixture: ComponentFixture<ContaPagamentoComponent>;
        let service: ContaPagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [ContaPagamentoComponent],
                providers: []
            })
                .overrideTemplate(ContaPagamentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContaPagamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContaPagamentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContaPagamento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contaPagamentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
