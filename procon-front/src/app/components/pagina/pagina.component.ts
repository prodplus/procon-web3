import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-pagina',
  templateUrl: './pagina.component.html',
  styleUrls: ['./pagina.component.css']
})
export class PaginaComponent implements OnInit {
  @Input() isLoading = false;

  constructor() { }

  ngOnInit(): void {
  }

}
