/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { PretComponent } from '../../../../../../main/webapp/app/entities/pret/pret.component';
import { PretService } from '../../../../../../main/webapp/app/entities/pret/pret.service';
import { Pret } from '../../../../../../main/webapp/app/entities/pret/pret.model';

describe('Component Tests', () => {

    describe('Pret Management Component', () => {
        let comp: PretComponent;
        let fixture: ComponentFixture<PretComponent>;
        let service: PretService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [PretComponent],
                providers: [
                    PretService
                ]
            })
            .overrideTemplate(PretComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PretComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PretService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Pret(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
