/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InvestimentoService } from 'app/entities/investimento/investimento.service';
import { IInvestimento, Investimento } from 'app/shared/model/investimento.model';

describe('Service Tests', () => {
    describe('Investimento Service', () => {
        let injector: TestBed;
        let service: InvestimentoService;
        let httpMock: HttpTestingController;
        let elemDefault: IInvestimento;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InvestimentoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Investimento(0, currentDate, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataAplicacao: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Investimento', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataAplicacao: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataAplicacao: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Investimento(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Investimento', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataAplicacao: currentDate.format(DATE_FORMAT),
                        qtdeCota: 1,
                        vlrCota: 1,
                        pctPrePosFixado: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataAplicacao: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Investimento', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataAplicacao: currentDate.format(DATE_FORMAT),
                        qtdeCota: 1,
                        vlrCota: 1,
                        pctPrePosFixado: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataAplicacao: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Investimento', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
