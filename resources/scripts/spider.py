import scrapy
import os
import json
import time
import logging

class BlogSpider(scrapy.Spider):
    name = 'blogspider'
    json_file = open('ks.json', 'r')
    start_urls = []
    custom_settings = {'USER_AGENT' : 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:39.0) Gecko/20100101 Firefox/39.0','DOWNLOAD_DELAY':0.2}
    for line in json_file:
		arr = json.loads(line)
		url = arr['data']['urls']['web']['project']	
		url = url[:url.find('?')] + "/community?_=" + str(int(time.time() * 100))
		start_urls.append(url)
		if (len(start_urls) == 100000):
			break
    start_urls = start_urls[1000:]
    def parse(self, response):
		backer_cities = []
		backer_countries = []
		backers = []
		for title in response.css('#content-wrap > div.NS_projects__content.pt11 > section.js-project-community-content.js-project-content.project-content > div > div.community-section__locations.js-community-locations > div > div > div:nth-child(1) > div'):
			for list_item in title.css('.location-list__item.js-location-item'):
				item = {}
				item['city'] = list_item.css('.primary-text *::text').extract()[1].strip()
				item['country'] = list_item.css('.secondary-text *::text').extract()[1].strip()
				item['backers'] = int(list_item.css('.tertiary-text *::text').extract()[0].strip().split(' ')[0])
				backer_cities.append(item)
		for title in response.css('#content-wrap > div.NS_projects__content.pt11 > section.js-project-community-content.js-project-content.project-content > div > div.community-section__locations.js-community-locations > div > div > div:nth-child(2) > div > div.location-list-wrapper.js-location-list-wrapper > div'):
				for list_item in title.css('.location-list__item.js-location-item'):
					item = {}
					item['country'] = list_item.css('.primary-text *::text').extract()[1].strip()
					item['backers'] = int(list_item.css('.tertiary-text *::text').extract()[0].strip().split(' ')[0].replace(',',''))
					backer_countries.append(item)

		backer_count = int(response.css('#content-wrap > div.NS_projects__content.pt11 > section.js-project-community-content.js-project-content.project-content > div > div.community-section__hero > div > div *::text').extract_first().strip().split(' ')[0].replace(',',''))

		backers = []

		for title in response.css('#content-wrap > div.NS_projects__content.pt11 > section.js-project-community-content.js-project-content.project-content > div > div.community-section__founding_backers > div > div.row.js-founding-backers'):
			for backer_box in response.css('.founding-backer.community-block-content'):
				item = {}
				item['backings'] = int(backer_box.css('.backing-count.js-founding-backer-backings *::text').extract_first().strip().split(' ')[1].replace(',',''))
				item['name'] = backer_box.css('.name.js-founding-backer-name *::text').extract_first().strip()
				item['avatar'] = backer_box.css('img::attr(src)').extract_first().split()
				backers.append(item)
		#request = scrapy.Request(response.request.url.split('?')[0].replace('community','founding_backers?page=1'), callback=self.parse_page2,  errback=self.errback_httpbin, cookies=[], headers={'Accept':'*/*'})
	#	request.meta['item'] =
		yield  {'url' : response.request.url, 'cities': backer_cities, 'countries': backer_countries, 'b_count' : backer_count, 'backers':backers}
		#return request.meta['item']

    def parse_page2(self, response):
		obj = response.meta['item']
		for t in json.loads(response.body_as_unicode()):
			obj['backers'].append(t)
		request = scrapy.Request(response.request.url.replace('page=1', 'page=2'), callback=self.parse_page3,errback=self.errback_httpbin,cookies=[], headers={'Accept':'*/*'})
		request.meta['item'] = obj
		return request

    def parse_page3(self, response):
 		obj = response.meta['item']
                for t in json.loads(response.body_as_unicode()):
                        obj['backers'].append(t)
                yield obj

    def errback_httpbin(self, failure):
		self.logger.error(repr(failure))

BlogSpider()
