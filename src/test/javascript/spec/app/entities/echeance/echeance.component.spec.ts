/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { EcheanceComponent } from '../../../../../../main/webapp/app/entities/echeance/echeance.component';
import { EcheanceService } from '../../../../../../main/webapp/app/entities/echeance/echeance.service';
import { Echeance } from '../../../../../../main/webapp/app/entities/echeance/echeance.model';

describe('Component Tests', () => {

    describe('Echeance Management Component', () => {
        let comp: EcheanceComponent;
        let fixture: ComponentFixture<EcheanceComponent>;
        let service: EcheanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [EcheanceComponent],
                providers: [
                    EcheanceService
                ]
            })
            .overrideTemplate(EcheanceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EcheanceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EcheanceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Echeance(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.echeances[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
