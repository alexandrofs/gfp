/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { IndiceSerieDiUpdateComponent } from 'app/entities/indice-serie-di/indice-serie-di-update.component';
import { IndiceSerieDiService } from 'app/entities/indice-serie-di/indice-serie-di.service';
import { IndiceSerieDi } from 'app/shared/model/indice-serie-di.model';

describe('Component Tests', () => {
    describe('IndiceSerieDi Management Update Component', () => {
        let comp: IndiceSerieDiUpdateComponent;
        let fixture: ComponentFixture<IndiceSerieDiUpdateComponent>;
        let service: IndiceSerieDiService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [IndiceSerieDiUpdateComponent]
            })
                .overrideTemplate(IndiceSerieDiUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IndiceSerieDiUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndiceSerieDiService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new IndiceSerieDi(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.indiceSerieDi = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new IndiceSerieDi();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.indiceSerieDi = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
