/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { ParametrageDetailComponent } from '../../../../../../main/webapp/app/entities/parametrage/parametrage-detail.component';
import { ParametrageService } from '../../../../../../main/webapp/app/entities/parametrage/parametrage.service';
import { Parametrage } from '../../../../../../main/webapp/app/entities/parametrage/parametrage.model';

describe('Component Tests', () => {

    describe('Parametrage Management Detail Component', () => {
        let comp: ParametrageDetailComponent;
        let fixture: ComponentFixture<ParametrageDetailComponent>;
        let service: ParametrageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ParametrageDetailComponent],
                providers: [
                    ParametrageService
                ]
            })
            .overrideTemplate(ParametrageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametrageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametrageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Parametrage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parametrage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
