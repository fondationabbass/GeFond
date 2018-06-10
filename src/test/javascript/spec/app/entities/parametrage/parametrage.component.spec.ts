/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { ParametrageComponent } from '../../../../../../main/webapp/app/entities/parametrage/parametrage.component';
import { ParametrageService } from '../../../../../../main/webapp/app/entities/parametrage/parametrage.service';
import { Parametrage } from '../../../../../../main/webapp/app/entities/parametrage/parametrage.model';

describe('Component Tests', () => {

    describe('Parametrage Management Component', () => {
        let comp: ParametrageComponent;
        let fixture: ComponentFixture<ParametrageComponent>;
        let service: ParametrageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ParametrageComponent],
                providers: [
                    ParametrageService
                ]
            })
            .overrideTemplate(ParametrageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametrageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametrageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Parametrage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parametrages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
