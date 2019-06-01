/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { CategoriaDespesaComponent } from 'app/entities/categoria-despesa/categoria-despesa.component';
import { CategoriaDespesaService } from 'app/entities/categoria-despesa/categoria-despesa.service';
import { CategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

describe('Component Tests', () => {
    describe('CategoriaDespesa Management Component', () => {
        let comp: CategoriaDespesaComponent;
        let fixture: ComponentFixture<CategoriaDespesaComponent>;
        let service: CategoriaDespesaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CategoriaDespesaComponent],
                providers: []
            })
                .overrideTemplate(CategoriaDespesaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoriaDespesaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaDespesaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CategoriaDespesa(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoriaDespesas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
