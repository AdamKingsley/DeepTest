import { NeuronsChart } from "./neurons.chart";

declare var d3;
export class NeuronChart {

  margin = { top: 55, right: 5, bottom: 5, left: 55 };
  r = 8;
  neuronMaxNum = 16;
  neuronGap = 20;
  layerGap = 20;

  static delta = 0.000001;

  colors = ['#777', '#5fc', '#c63', '#c36'];
  strokeColor = '#333';

  target: HTMLElement;

  constructor(target: HTMLElement) {
    this.target = target;
  }

  render(standardData: number[][]): void {

    console.log('start rendering');

    let width: number = this.target.getBoundingClientRect().width;
    let height: number = 500;

    this.margin.left = (width - this.neuronMaxNum * this.neuronGap) / 2;
    this.margin.right = this.margin.left;

    d3.select(this.target)
      .append('text')
      .classed('title', true)
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap / 2 - 36)
      .attr('y', 25)
      .attr('fill', 'black')
      .attr('style', 'font-size: 18px')
      .text('标准模型');

    let top: number = this.margin.top;

    for (let index = 0; index < standardData.length; index++) {
      d3.select(this.target)
        .selectAll('circle .standard-layer' + index)
        .data(standardData[index])
        .enter()
        .append('circle')
        .classed('standard-layer' + index, true)
        .attr('style', 'cursor: pointer')
        .attr('fill', d => {
          if (d < NeuronChart.delta) {
            return this.colors[0];
          } else {
            return this.colors[1];
          }
        })
        .attr('r', this.r)
        .attr('stroke', this.strokeColor)
        .attr('stroke-width', 1)
        .attr('transform', (d, i) => {
          let offsetX: number = this.margin.left + (i % this.neuronMaxNum) * this.neuronGap;
          let offsetY: number = top + Math.trunc(i / this.neuronMaxNum) * this.neuronGap;
          return `translate(${offsetX}, ${offsetY})`;
        })
        .on('mouseover', function(d: number) {
          let pos = d3.mouse(this);
          let tip = document.getElementById('neuron-details');
          let left = this.getBoundingClientRect().left + pos[0];
          let top = this.getBoundingClientRect().top + pos[1];

          tip.style.left = (left + 20) + 'px';
          tip.style.top = (top + 20) + 'px';

          tip.innerHTML = NeuronChart.getDetails(d);

          tip.style.display = 'block';
        })
        .on('mouseout', function() {
          let tip = document.getElementById('neuron-details');
          tip.style.display = 'none';
        })
        .attr('opacity', 0)
        .transition()
        .duration(800)
        .attr('opacity', 1);
      d3.select(this.target)
        .append('text')
        .classed('layer-num', true)
        .attr('x', this.margin.left - 60)
        .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
        .attr('fill', 'black')
        .attr('style', 'font-size: 12px')
        .text('第' + (index + 1) +"层");

      top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
    }

    this.target.style.height = top + 'px';
  }

  private static getDetails(value): string {
    let state;
    if (Math.abs(value) < NeuronsChart.delta) {
      state = '未激活';
    } else {
      state = '激活';
    }
    return `激活状态: ${state}<br/>激活值: ${value == 0 ? value : value.toFixed(6)}`;
  }

}
